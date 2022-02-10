package com.api.env.resources;

import com.util.cloud.ConfigurationManager;
import com.util.cloud.Environment;

/**
 * Application external resources
 */
@SuppressWarnings("unchecked")
public enum AppResources {

    /**
     * Url to be sent in the confirmation email.
     */
    ACCOUNT_CONFIRMATION_URL {
        public String value() {
            return Environment.getProperty("APP_URL", configuration.getPropertyAsString("app.url")) + "/confirm";
        }
    },
    /**
     * Base URL of the application;
     */
    APP_URL {
        public String value() {
            return Environment.getProperty("APP_URL", configuration.getPropertyAsString("app.url"));
        }
    },

    ENCRYPTION_KEY {
        public String value() {
            return Environment.getProperty("ENCRYPTION_KEY", configuration.getPropertyAsString("encryption.key"));
        }
    },

    DB_HOSTNAME {
        public String value() {
            return Environment.getProperty("DB_HOSTNAME", configuration.getPropertyAsString("db.hostname"));
        }
    },

    DB_USER {
        public String value() {
            return Environment.getProperty("DB_USER", configuration.getPropertyAsString("db.user"));
        }
    },

    DB_PASSWORD {
        public String value() {
            return Environment.getProperty("DB_PASSWORD", configuration.getPropertyAsString("db.password"));
        }
    },
    OTP_LOGIN_URL {
        public String value() {
            return Environment.getProperty("APP_URL", configuration.getPropertyAsString("app.url")) + "/otp-login";
        }
    },
    RABBITMQ_EXCHANGE_DIRECT {
        public String value() {
            return Environment.getProperty("RABBITMQ_EXCHANGE_DIRECT", configuration.getPropertyAsString("app.rabbitmq.exchange.direct"));
        }
    },
    RABBITMQ_EXCHANGE_DIRECT_DL {
        public String value() {
            return Environment.getProperty("RABBITMQ_EXCHANGE_DIRECT_DL", configuration.getPropertyAsString("app.rabbitmq.exchange.direct.dl"));
        }
    },
    RABBITMQ_EXCHANGE_FANOUT {
        public String value() {
            return Environment.getProperty("RABBITMQ_EXCHANGE_FANOUT", configuration.getPropertyAsString("app.rabbitmq.exchange.fanout"));
        }
    },
    RABBITMQ_EXCHANGE_FANOUT_DL {
        public String value() {
            return Environment.getProperty("RABBITMQ_EXCHANGE_FANOUT_DL", configuration.getPropertyAsString("app.rabbitmq.exchange.fanout.dl"));
        }
    },
    RABBITMQ_DIRECT_QUEUE {
        public String value() {
            return Environment.getProperty("RABBITMQ_DIRECT_QUEUE", configuration.getPropertyAsString("app.rabbitmq.direct.queue"));
        }
    },
    RABBITMQ_DIRECT_QUEUE_DL {
        public String value() {
            return Environment.getProperty("RABBITMQ_DIRECT_QUEUE_DL", configuration.getPropertyAsString("app.rabbitmq.direct.queue.dl"));
        }
    },
    RABBITMQ_FANOUT_QUEUE {
        public String value() {
            return Environment.getProperty("RABBITMQ_FANOUT_QUEUE", configuration.getPropertyAsString("app.rabbitmq.fanout.queue"));
        }
    },
    RABBITMQ_FANOUT_QUEUE_DL {
        public String value() {
            return Environment.getProperty("RABBITMQ_FANOUT_QUEUE_DL", configuration.getPropertyAsString("app.rabbitmq.fanout.queue.dl"));
        }
    },
    RABBITMQ_DIRECT_ROUTING_KEY {
        public String value() {
            return Environment.getProperty("RABBITMQ_DIRECT_ROUTING_KEY", configuration.getPropertyAsString("app.rabbitmq.direct.routingKey"));
        }
    },
    RABBITMQ_DIRECT_ROUTING_KEY_DL {
        public String value() {
            return Environment.getProperty("RABBITMQ_DIRECT_ROUTING_KEY_DL", configuration.getPropertyAsString("app.rabbitmq.direct.routingKey.dl"));
        }
    },
    RABBITMQ_HOST {
        public String value() {
            return Environment.getProperty("RABBITMQ_HOST", configuration.getPropertyAsString("app.rabbitmq.host"));
        }
    },
    RABBITMQ_USERNAME {
        public String value() {
            return Environment.getProperty("RABBITMQ_USERNAME", configuration.getPropertyAsString("app.rabbitmq.username"));
        }
    },
    RABBITMQ_PASSWORD {
        public String value() {
            return Environment.getProperty("RABBITMQ_PASSWORD", configuration.getPropertyAsString("app.rabbitmq.password"));
        }
    },
    RABBITMQ_MAX_CONSUMERS {
        public String value() {
            return Environment.getProperty("RABBITMQ_MAX_CONSUMERS", configuration.getPropertyAsString("app.rabbitmq.max.consumers"));
        }
    };
    private static final com.util.cloud.Configuration configuration = ConfigurationManager.getConfiguration();

    public abstract <T> T value();

}
