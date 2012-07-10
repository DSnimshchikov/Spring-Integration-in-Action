package siia.monitoring.controlbus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ControlBusDemo {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml", ControlBusDemo.class);
		NumberHolder numberHolder = context.getBean("numberHolder", NumberHolder.class);
		MessageChannel controlChannel = context.getBean("controlChannel", MessageChannel.class);
		System.out.println("number before increment: " + numberHolder.getNumber());
		Message<String> message = MessageBuilder.withPayload("@numberHolder.increment()").build();
		controlChannel.send(message);
		System.out.println("number after increment:  " + numberHolder.getNumber());
		ThreadPoolTaskExecutor executor = context.getBean("myExecutor", ThreadPoolTaskExecutor.class);
		System.out.println("max pool size before update: " + executor.getMaxPoolSize());
		controlChannel.send(MessageBuilder.withPayload("@myExecutor.setMaxPoolSize(25)").build());
		System.out.println("max pool size after update:  " + executor.getMaxPoolSize());
	}

}
