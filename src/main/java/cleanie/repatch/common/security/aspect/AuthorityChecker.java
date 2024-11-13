package cleanie.repatch.common.security.aspect;

import cleanie.repatch.common.exception.UnAuthorizedAccessException;
import cleanie.repatch.common.security.domain.Accessor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_AUTHORITY;

@Aspect
@Component
public class AuthorityChecker {

    @Before("@annotation(cleanie.repatch.common.security.annotation.MemberOnly)")
    public void checkMember(final JoinPoint joinPoint) {
        checkAuthority(joinPoint, Accessor::isMember);
    }

    @Before("@annotation(cleanie.repatch.common.security.annotation.BuyerOnly)")
    public void checkBuyer(final JoinPoint joinPoint) {
        checkAuthority(joinPoint, Accessor::isBuyer);
    }

    @Before("@annotation(cleanie.repatch.common.security.annotation.SellerOnly)")
    public void checkSeller(final JoinPoint joinPoint) {
        checkAuthority(joinPoint, Accessor::isSeller);
    }

    private void checkAuthority(JoinPoint joinPoint, Predicate<Accessor> authorityCheck) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(authorityCheck)
                .findFirst()
                .orElseThrow(() -> new UnAuthorizedAccessException(INVALID_AUTHORITY));
    }
}
