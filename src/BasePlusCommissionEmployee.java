
public class BasePlusCommissionEmployee extends CommissionEmployee {
    private double baseSalary = 150000;

    public BasePlusCommissionEmployee(String firstName, String lastName, String socialSecurityNumber, double grossSales, double commissionRate, double baseSalary) {
        super(firstName, lastName, socialSecurityNumber, grossSales, commissionRate);
        this.baseSalary = baseSalary > 0 ? baseSalary : 150000;

    }

    @Override
    public double getPaymentAmount() {
        return baseSalary + super.getPaymentAmount();
    }
}