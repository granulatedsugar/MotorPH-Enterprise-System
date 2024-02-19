package com.mes.motorph.services;

public class DeductionService {
    private double sssDeduction;
    private double philHealthDeduction;
    private double pagIbigDeduction;
    private double withHoldingTax;

    public double calculateSssDeduction(double salary) {

        // TODO: Phase 2 improvement, move to DB.
        int[] Social = {3250, 3750, 4250, 4750, 5250, 5750, 6250, 6750, 7250, 7750, 8250, 8750, 9250, 9750, 10250, 10750, 11250, 11750, 12250, 12750, 13250, 14250, 14750, 15250, 15750, 16250, 16750, 17250, 17750, 18250, 18750, 19250, 19750, 20250, 20750, 21250, 21750, 22250, 22750, 24250, 24750};
        double[] Bracket = {135, 157.50, 180, 202.50, 225, 247.50, 270, 292.50, 315, 337.50, 360, 382.50, 405, 427.50, 450, 472.50, 495, 517.50, 540, 562.50, 85, 607.50, 630, 652.50, 675, 697.50, 720, 742.50, 765, 787.50, 810, 832.50, 855, 877.50, 900, 922.50, 945, 967.50, 990, 1012.50, 1035, 1057.50, 1080, 1102.50, 1125};

        // Loop through each value until it finds the closes value and use
        // the index of that value to return the bracket value
        for (int i = 0; i < Social.length; i++) {
            if (salary <= Social[i]) {
                sssDeduction = Bracket[i];
                break;
            } else {
                // If salary is over the SSS salary bracket
                // then the default Bracket deduction will be 1,125.00
                sssDeduction = Bracket[Bracket.length - 1];
            }
        }
        return sssDeduction;
    }

    public double calculatePhilHealthDeduction(double Salary) {
        double philHealthPremiumRate = 0.03;

        // Salary multiplied to the Premium Rate divided to 2 to get the
        // total PhilHealth Deduction
        philHealthDeduction = (Salary * philHealthPremiumRate) / 2;

        return philHealthDeduction;
    }

    public double calculatePagIbigDeduction(double Salary) {
        // PagIbig Deductions are based on salary brackets
        // If salary bracket is between 1000 and 1500 then its 1%
        // else it would be 2% and if the deduction is > 100
        // then the max deduction is 100
        if (Salary >= 1000 && Salary <= 1500) {
            pagIbigDeduction = (Salary * 0.01);
        } else {
            pagIbigDeduction = (Salary * 0.02);
        }

        if (pagIbigDeduction > 100) {
            pagIbigDeduction = 100;
        }

        return pagIbigDeduction;
    }

    public double calculateWithholdingTax(double Salary) {
        // TODO: Phase 2 improvement, move to DB.
        double[] monthlyRate = {20833, 33333, 66667, 166667, 666667};
        double[] taxRate = {0, 0.2, 0.25, 0.30, 0.32, 0.35};
        double[] additional = {0, 0, 2500, 10833, 40833.33, 200833.33};
        double[] deduction = {0, 20833, 33333, 66667, 166667, 666667};

        for (int i = 0; i < monthlyRate.length; i++) {
            if (Salary <= monthlyRate[i]) {
                withHoldingTax = Salary - deduction[i];
                withHoldingTax = (withHoldingTax * taxRate[i]) + additional[i];
                break;
            } else {
                withHoldingTax = (Salary - monthlyRate[monthlyRate.length - 1]) * taxRate[taxRate.length - 1] + additional[additional.length - 1];
            }
        }
        return withHoldingTax;
    }
}