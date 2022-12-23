package ru.job4j.accidents.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccidentMapper implements RowMapper<Accident> {

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt(1));
        accident.setName(rs.getString(2));
        accident.setText(rs.getString(3));
        accident.setAddress(rs.getString(4));
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt(6));
        accidentType.setName(rs.getString(7));
        accident.setType(accidentType);
        return accident;
    }
}