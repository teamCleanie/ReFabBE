package cleanie.repatch.setting;

import cleanie.repatch.setting.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DummyTest {

    @DisplayName("더미 테스트 (삭제 필요)")
    @UnitTest
    @Test
    void dummyTest() {
        assertThat(true).isTrue();
    }
}
