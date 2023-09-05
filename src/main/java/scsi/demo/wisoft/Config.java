package scsi.demo.wisoft;

import java.sql.ResultSet;
import java.util.Properties;

public class Config {
    Properties prop = null;

    String propertiesPath = "";
    String dbConfigPath;
    String dbType;
    String connectType;
    String username;
    String password;
    int resultSetType = ResultSet.TYPE_FORWARD_ONLY;
    int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;

    public Config () {
    }

    public Config (String path) {
        if (path.trim().isEmpty()) {
            init("../application.properties");
        } else {
            init(path);
        }
    }

    private void init (String path) {
        try {
            String tmp = "";
            this.propertiesPath = path;
            prop = new Properties();
            prop.load(this.getClass().getClassLoader().getResourceAsStream(path));

            tmp = getValueFromPropertyByKey("dataSourcePath");
            dbConfigPath = tmp.isEmpty() ? "jdbc/scsi" : tmp;

            switch (getValueFromPropertyByKey("resultSetType")) {
                case "ResultSet.TYPE_SCROLL_INSENSITIVE":
                    resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
                    break;
                case "ResultSet.TYPE_SCROLL_SENSITIVE":
                    resultSetType = ResultSet.TYPE_SCROLL_SENSITIVE;
                    break;
                default:
                    resultSetType = ResultSet.TYPE_FORWARD_ONLY;
            }

            tmp = getValueFromPropertyByKey("resultSetConcurrency");
            if (tmp.equals("ResultSet.CONCUR_UPDATABLE")) {
                resultSetConcurrency = ResultSet.CONCUR_UPDATABLE;
            } else {
                resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
            }

            tmp = getValueFromPropertyByKey("connectType");
            connectType = tmp.isEmpty() ? "JNDI" : tmp;
            if (connectType.equals("URL_ONLY")) {
                username = getValueFromPropertyByKey("username");
                password = getValueFromPropertyByKey("password");
            }
        } catch (Exception e) {
            //LogUtil.logger.warning("指定路徑下查無檔案: " + path);
        }
    }

    private String getValueFromPropertyByKey (String propertyKey) {
        String tmp = prop.getProperty(propertyKey);
        if (tmp != null) {
            return tmp;
        }
        return "";
    }
}
