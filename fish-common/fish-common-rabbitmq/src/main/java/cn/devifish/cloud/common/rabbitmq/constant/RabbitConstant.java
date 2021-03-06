package cn.devifish.cloud.common.rabbitmq.constant;

/**
 * RabbitmqConstant
 * RabbitMQ相关常量
 *
 * @author Devifish
 * @date 2020/8/10 17:35
 */
public interface RabbitConstant {

    /** 名称分隔符 **/
    String NAME_SEPARATOR = "-";

    /** 交换机名称分隔符 **/
    String EXCHANGE_NAME_SEPARATOR = ".";

    /** 交换机名称后缀 **/
    String EXCHANGE_NAME_SUFFIX = EXCHANGE_NAME_SEPARATOR + "exchange";

    /** 路由名称后缀 **/
    String ROUTING_NAME_SUFFIX = NAME_SEPARATOR + "routing";

    /** 队列名称后缀 **/
    String QUEUE_NAME_SUFFIX = NAME_SEPARATOR + "queue";

}
