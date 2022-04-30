public class HelloIf {
    public static void main(String[] args) {
        int[] num_arr = new int[]{1,2,3,4};
        double result = 0;
        for (int i = 0; i < num_arr.length; i++) {
            if (i==0) {
                result = result + num_arr[i];
            } else if(i==1){
                result = result - num_arr[i];
            } else if(i==2){
                result = result * num_arr[i];
            } else{
                result = result / num_arr[i];
            }
        }
    }
}
