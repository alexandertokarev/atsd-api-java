/*
 * Copyright 2015 Axibase Corporation or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * https://www.axibase.com/atsd/axibase-apache-2.0.pdf
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.axibase.tsd.example;

import com.axibase.tsd.model.data.command.GetSeriesQuery;
import com.axibase.tsd.model.data.command.SimpleAggregateMatcher;
import com.axibase.tsd.model.data.series.*;
import com.axibase.tsd.model.data.series.aggregate.AggregateType;
import com.axibase.tsd.model.meta.EntityAndTags;
import com.axibase.tsd.model.meta.Metric;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikolay Malevanny.
 */
public class ExportCsvExample extends AbstractAtsdClientExample {

    public static void main(String[] args) throws Exception {
        ExportCsvExample example = new ExportCsvExample();
        example.configure();
        example.downloadAndSave();
    }

    private void downloadAndSave() throws IOException {
        // define parameters
        final String datePattern = "yyyy-MM-dd HH:mm:ss.SSS";
        final String numberPattern = "###.000";

        final String filePath = "/tmp/atsd/export.csv";
        final String entityName = "atsd";
        final String metricName = "jvm_memory_free";
        final long startTime = System.currentTimeMillis() - 36000;
        final long endTime = System.currentTimeMillis();
        Map<String, String> tags = new HashMap<String, String>();
//        tags.put("host","localhost");

        // do work
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        NumberFormat numberFormat = new DecimalFormat(numberPattern);
        GetSeriesQuery command = new GetSeriesQuery(entityName, metricName, tags,
                startTime, endTime);
        List<GetSeriesResult> seriesResultList = dataService.retrieveSeries(command);

        final FileOutputStream outputStream = FileUtils.openOutputStream(new File(filePath));
        PrintWriter writer = new PrintWriter(outputStream);

        try {
            for (int i = 0; i < seriesResultList.size(); i++) {
                GetSeriesResult seriesResult = seriesResultList.get(i);
                System.out.println("Time series key [" + i + "]: " + seriesResult.getTimeSeriesKey());
            }
            if (seriesResultList.size() != 1) {
                throw new IllegalArgumentException("Select the other parameters (entity, metric, tags) " +
                        "to export single time series key");
            }

            final List<Series> seriesList = seriesResultList.get(0).getData();
            for (Series series : seriesList) {
                final Date date = new Date(series.getTimeMillis());
                final double value = series.getValue();
                writer.println(dateFormat.format(date) + "," + numberFormat.format(value));
            }
            System.out.println("Saved " + seriesList.size() + " values");
            writer.flush();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writer);
        }
    }
}