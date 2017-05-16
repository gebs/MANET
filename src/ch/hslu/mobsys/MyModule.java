package ch.hslu.mobsys;

import ch.hslu.mobsys.protocol.ReceiveService;
import ch.hslu.mobsys.protocol.SendService;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gebs on 5/16/17.
 */
public class MyModule extends AbstractModule {
    protected void configure() {

        final EventBus eventBus = new EventBus();

        bind(EventBus.class).toInstance(eventBus);

        bindListener(Matchers.any(), new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    public void afterInjection(I i) {
                        eventBus.register(i);
                    }
                });
            }
        });


        bind(MessageHandler.class).asEagerSingleton();


    }


    @Provides
    public ServiceManager serviceManager(Set<Service> services) {
        return new ServiceManager(services);
    }

    @Provides
    public Set<Service> services(ReceiveService receiveService, SendService sendService) {

        Set<Service> services = new HashSet<Service>();

        services.add(receiveService);
        return services;
    }

    @Provides
    @Singleton
    public MulticastSocket getMulticastSocket() throws IOException {
        MulticastSocket socket = new MulticastSocket(1337);
        socket.setLoopbackMode(false);
        InetAddress group = InetAddress.getByName("239.255.255.250");
        socket.joinGroup(group);

        return socket;
    }
}
