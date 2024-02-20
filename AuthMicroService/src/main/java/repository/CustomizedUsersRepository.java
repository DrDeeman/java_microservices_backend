package repository;

import entity.eUsers;
import records.TestCustomRecord;

import java.util.List;


public interface CustomizedUsersRepository {
    eUsers getCustomUserByLogin(String login);
    List<TestCustomRecord> getListGroupEmail();
}
