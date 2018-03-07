/**
 * Created by W7HP on 2017-11-23.
 */
public class IntegralCalculation {

    static double function(double x, double y) {
        return 2 * x * x * y * y + 6 * x + 5;
    }

    static double calculateIntegral(int numberOfPoints, double pointsArray[], double weightsArray[]) {
        double result = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            for (int j = 0; j < numberOfPoints; j++) {
                result += weightsArray[i] * weightsArray[j] * function(pointsArray[i], pointsArray[j]);
            }
        }
        return result;
    }
}


//        unsigned int numberOfPoints=3;
//        double pointsArray[3]={-0.77,0,0.77};
//        double weightsArray[3]={5./9.,8./9.,5./9.};
//        Sysytem.out.println("3 points: "+calculateIntegral(numberOfPoints,pointsArray,weightsArray));
//
//         numberOfPoints=2;
//         pointsArray[0]=-0.57;
//         pointsArray[1]=0.57;
//         weightsArray[0]=1;
//         weightsArray[1]=1;
//         Sysytem.out.println("2 points: "+calculateIntegral(numberOfPoints, pointsArray, weightsArray));
