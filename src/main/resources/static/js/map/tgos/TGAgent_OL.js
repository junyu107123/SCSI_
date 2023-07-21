// TODO : 圖磚代理程式的網址

var getTileAgentPath = function()
{
    return ("api.tgos.tw/");  // keep last '/'
};

//=== 修改上面的 Agent 位址 ===========================================================

var getWebProtocal = function()
{
    return ('https:' == document.location.protocol ? 'https://' : 'https://');
};
var TGOS = TGOS ||
{
    TILE_AGENT_PATH : getWebProtocal() + getTileAgentPath() + "TileAgent"
};

TGOS.CoordSys = TGOS.CoordSys || {};

TGOS.CoordSys = {
    EPSG3857: 'EPSG3857',
    EPSG4326: 'EPSG4326',
    EPSG3826: 'EPSG3826',
    EPSG3825: 'EPSG3825'
};

TGOS.TGMapTileId = TGOS.TGMapTileId || {};

TGOS.TGMapTileId = {
    TGOSMAP: 'TGOSMAP',
    F2IMAGE: 'F2IMAGE',
    ROADMAP: 'ROADMAP',
    HILLSHADE: 'HILLSHADE',
    HILLSHADEMIX: 'HILLSHADEMIX',
    MOTCMAP: 'MOTCMAP',
    NLSCMAP: 'NLSCMAP',
    CITYZONING: 'CITYZONING',
    RURALZONING: 'RURALZONING',
    LANDUSE: 'LANDUSE',
    TOPO1000: 'TOPO1000'
};

TGOS.getTileAgentPath = function(mapTileId, crs)
{
    var path = TGOS.TILE_AGENT_PATH + '/' + mapTileId;
    if (crs == 'EPSG3857')
    {
        path += '_W';
    }
    if (crs == 'EPSG3825')
    {
        path += '_119';
    }
    path += '.aspx';
    
    return path;
};

TGOS.TGKey = function(appID, apiKey)
{
    var appID_ = appID;
    var apiKey_ = apiKey;
    
    this.setAppID = function(id)
    {
        appID_ = id;
    };
    
    this.getAppID = function()
    {
        return appID_;
    };
    
    this.setApiKey = function(key)
    {
        apiKey_ = key;
    };
    
    this.getApiKey = function()
    {
        return apiKey_;
    };
};
