package com.mes.motorph.services;

public class DeductionService {
    private double sssDeduction;
    private double philHealthDeduction;
    private double pagIbigDeduction;
    private double withHoldingTax;
    int[] Social = {3250, 3750, 4250, 4750, 5250, 5750, 6250, 6750, 7250, 7750, 8250, 8750, 9250, 9750, 10250, 10750, 11250, 11750, 12250, 12750, 13250, 14250, 14750, 15250, 15750, 16250, 16750, 17250, 17750, 18250, 18750, 19250, 19750, 20250, 20750, 21250, 21750, 22250, 22750, 24250, 24750};
    double[] Bracket = {135, 157.50, 180, 202.50, 225, 247.50, 270, 292.50, 315, 337.50, 360, 382.50, 405, 427.50, 450, 472.50, 495, 517.50, 540, 562.50, 85, 607.50, 630, 652.50, 675, 697.50, 720, 742.50, 765, 787.50, 810, 832.50, 855, 877.50, 900, 922.50, 945, 967.50, 990, 1012.50, 1035, 1057.50, 1080, 1102.50, 1125};


    public double calculateSssDeduction(double salary) {
        for (int i = 0; i < Social.length; i++) {
            if (salary <= Social[i]) {
                sssDeduction = Bracket[i];
                break;
            } else {
                sssDeduction = Bracket[Bracket.length - 1];
            }
        }
        return sssDeduction;
    }
}