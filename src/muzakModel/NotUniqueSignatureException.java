package muzakModel;

public class NotUniqueSignatureException extends Exception
{
    private static final long serialVersionUID = -4716769086186094021L;

    public NotUniqueSignatureException()
    {
        super();
    }
    
    public NotUniqueSignatureException(String msg)
    {
        super(msg);
    }
    
    public String getMessage()
    {
        return super.getMessage();
    }
}
