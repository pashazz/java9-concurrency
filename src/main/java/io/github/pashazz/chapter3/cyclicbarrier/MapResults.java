package io.github.pashazz.chapter3.cyclicbarrier;

/** This class stores an intermediate sums
 *

for each {@link io.github.pashazz.chapter3.cyclicbarrier.MatrixMock} row in an array
 */
public class MapResults {
   private  final int[] results;

   MapResults(int rowCount) {
       results = new int[rowCount];
   }
   public void setResultForRow(int rowIndex, int result) {
       results[rowIndex] = result;
   }

   public int[] getResults() {
       return results;
   }
}
