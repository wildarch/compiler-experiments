package parser;

public final class Logger
{
    private static boolean _debugEnabled;
    private static boolean _verboseEnabled;

    public static void init(boolean debugEnabled, boolean verboseEnabled)
    {
        _debugEnabled = debugEnabled;
        _verboseEnabled = verboseEnabled;
    }

    public static void debug(Object line)
    {
        if (_debugEnabled) {
            System.out.println("DEBUG: "+line);
        }
    }

    public static void info(Object line)
    {
        if (_verboseEnabled) {
            System.out.println("INFO: "+line);
        }
    }
}
