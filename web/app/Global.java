import configs.AppConfig;
import configs.DataConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class, DataConfig.class);
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        if (clazz.isAnnotationPresent(Component.class)
                || clazz.isAnnotationPresent(Controller.class)
                || clazz.isAnnotationPresent(Service.class)
                || clazz.isAnnotationPresent(Repository.class))
            return ctx.getBean(clazz);
        return null;
    }

}