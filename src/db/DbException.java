package db;

public class DbException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	//Creates a new RunTimeException so the Exception 
	//that aren't RunTime can be substituted by this one
	//like the SQLException
	
	public DbException(String msg) {
		super(msg);
	}
}
