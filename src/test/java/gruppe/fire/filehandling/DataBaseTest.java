package gruppe.fire.filehandling;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DataBaseTest {

  @Test
  void UnzipTest(){
    DataBase dataBase = new DataBase();
    assertThrows(NullPointerException.class, dataBase::gpathHandler);
  }
}
