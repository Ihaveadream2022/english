package com.english.exception;

public class ServiceRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private String serviceType = "[EMPTY] ";

    public ServiceRuntimeException(String message)
    {
        super(message);
    }

    public ServiceRuntimeException(String message, String serviceType)
    {
        super(message);

        this.serviceType = serviceType;
    }

    public String getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(String serviceType)
    {
        this.serviceType = serviceType;
    }
}