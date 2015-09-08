package general.aspect;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class NotOnWeekendsModule extends AbstractModule {
    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(NotOnWeekends.class),
                new WeekendBlocker());
    }
}
