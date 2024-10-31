package cleanie.repatch.common.log.proxy;

import cleanie.repatch.common.log.model.LoggingForm;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor
public class PreparedStatementProxyHandler implements MethodInterceptor {

    private static final List<String> JDBC_QUERY_METHOD = List.of("executeQuery", "execute", "executeUpdate");

    private final LoggingForm loggingForm;

    @Nullable
    @Override
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {

        final Method method = invocation.getMethod();

        if (JDBC_QUERY_METHOD.contains(method.getName())) {
            final long startTime = System.currentTimeMillis();
            final Object result = invocation.proceed();
            final long endTime = System.currentTimeMillis();

            loggingForm.queryExecuted(endTime - startTime);
            return result;
        }

        return invocation.proceed();
    }
}
