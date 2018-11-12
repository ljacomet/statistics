/*
 * All content copyright Terracotta, Inc., unless otherwise indicated.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.statistics.derived.histogram;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Ignore
public class BarSplittingBiasedHistogramPerformanceTest {

  @Test
  public void testNanoTime() throws IOException {
    long total = 0L;
    for (int i = 0; i < 2000000; i++) {
      long start = System.nanoTime();
      total += System.nanoTime() - start;
    }
    System.out.println("System.nanoTime() mean time (ns): " + (((double) total) / 2000000));
  }

  @Test
  public void testSelf() throws IOException {
    BarSplittingBiasedHistogram bsbh = new BarSplittingBiasedHistogram(0.75f, 20, 1000000);
    long last = 3000L;
    for (int i = 0; i < 2000000; i++) {
      long start = System.nanoTime();
      bsbh.event(last, i);
      last = System.nanoTime() - start;
    }
    System.out.println("Minimum " + Arrays.toString(bsbh.getQuantileBounds(0)));
    System.out.println("Median " + Arrays.toString(bsbh.getQuantileBounds(0.5)));
    System.out.println("90%ile " + Arrays.toString(bsbh.getQuantileBounds(0.9)));
    System.out.println("95%ile " + Arrays.toString(bsbh.getQuantileBounds(0.95)));
    System.out.println("99%ile " + Arrays.toString(bsbh.getQuantileBounds(0.99)));
    System.out.println("99.9%ile " + Arrays.toString(bsbh.getQuantileBounds(0.999)));
    System.out.println("99.99%ile " + Arrays.toString(bsbh.getQuantileBounds(0.9999)));
    System.out.println("99.999%ile " + Arrays.toString(bsbh.getQuantileBounds(0.99999)));
    System.out.println("Maximum " + Arrays.toString(bsbh.getQuantileBounds(1.0)));
  }

  @Test
  public void testData() throws IOException {
    BarSplittingBiasedHistogram bsbh = new BarSplittingBiasedHistogram(0.75f, 20, 1000000);
    Random rndm = new Random();
    long[] data = new long[2000000];
    for (int i = 0; i < data.length; i++) {
      data[i] = (long) (Math.abs(rndm.nextGaussian()) * 3000L);
    }
    final int cycles = 10;
    long fullStart = System.nanoTime();
    for (int c = 0; c < cycles; c++) {
      long start = System.nanoTime();
      for (int i = 0; i < data.length; i++) {
        bsbh.event(data[i], i);
      }
      long total = System.nanoTime() - start;
      System.out.println("\t" + c + " Mean Time (ns): " + ((double) total) / data.length);
    }
    long fullEnd = System.nanoTime() - fullStart;
    System.out.println("Mean Time (ns): " + ((double) fullEnd) / (cycles * data.length));
  }

}