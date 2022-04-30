public class HelloSwitch {
    public static void main(String[] args) {
        int[] num_arr = new int[]{1,2,3,4};
        double result = 0;
        for (int i = 0; i < num_arr.length; i++) {
            switch (i){
                case 0:
                    result = result + num_arr[i];
                    break;
                case 1:
                    result = result - num_arr[i];
                    break;
                case 2:
                    result = result * num_arr[i];
                    break;
                default:
                    result = result / num_arr[i];
            }
        }
    }
}
