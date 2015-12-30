package net.continuum.patch.model;

public class PatchDefinition
{
    private  String uuid;
    private  String product;
    private  String description;
    private  String updateType;
    private  String testStatus;
    private  boolean restartRequired;

    public PatchDefinition()
    {
        this.uuid = null;
        this.product = null;
        this.description = null;
        this.updateType = null;
        this.testStatus = null;
        this.restartRequired = true;
    }
    
    
    public PatchDefinition (String uuid, String product,
                            String description, String updateType,
                            String testStatus, boolean restartRequired)
    {
        this.uuid = uuid;
        this.product = product;
        this.description = description;
        this.updateType = updateType;
        this.testStatus = testStatus;
        this.restartRequired = restartRequired;
    }

    public PatchDefinition (String product,
                            String description, String updateType,
                            String testStatus, boolean restartRequired)
    {
        this.uuid = null;
        this.product = product;
        this.description = description;
        this.updateType = updateType;
        this.testStatus = testStatus;
        this.restartRequired = restartRequired;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    public String getUuid()
    {
        return uuid;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }
    public String getProduct()
    {
        return product;
    }

    public void setDescription(String desc)
    {
        this.description = desc;
    }
    public String getDescription()
    {
        return description;
    }

    public void setUpdateType(String updateType)
    {
        this.updateType = updateType;
    }
    public String getUpdateType()
    {
        return updateType;
    }

    public void setTestStatus(String testStatus)
    {
        this.testStatus = testStatus;
    }
    public String getTestStatus()
    {
        return testStatus;
    }

    public void setRestartRequired(boolean restart)
    {
        this.restartRequired = restart;
    }
    public boolean getRestartRequired()
    {
        return restartRequired;
    }
}
