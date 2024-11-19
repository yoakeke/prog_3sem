package ru.samarina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.samarina.MySecondTestAppSpringBoot.model.Positions;
import java.util.GregorianCalendar;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions position, double salary, double bonus, int workDays) {
        return salary * bonus * getDaysInYear() * position.getPositionCoefficient() / workDays;
    }

    public double calculateQuarterBonus(Positions position, double salary, double bonus, int workDays, int quarter, int year) {
        if (!position.isManager()) {
            return 0;
        }
        return salary * bonus * position.getPositionCoefficient() * getDaysInQuarter(quarter, year) / workDays;
    }

    static int getDaysInYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.getMaximum(GregorianCalendar.DAY_OF_YEAR);
    }

    static int getDaysInQuarter(int quarter, int year) {
        return switch (quarter) {
            case 1 -> 90 + (new GregorianCalendar().isLeapYear(year) ? 1 : 0); // Январь, февраль, март
            case 2 -> 91; // Апрель, май, июнь
            case 3 -> 92; // Июль, август, сентябрь
            case 4 -> 92; // Октябрь, ноябрь, декабрь
            default -> throw new IllegalArgumentException("Invalid quarter: " + quarter);
        };
    }
}
