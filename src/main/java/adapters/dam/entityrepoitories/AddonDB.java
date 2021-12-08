package adapters.dam.entityrepoitories;

import adapters.dam.DBGateway;
import businessrules.dai.Repository;
import entities.Addon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class AddonDB implements Repository<Addon> {
    DBGateway databaseConnector;
    final String tableName = "Addon";

    public AddonDB(DBGateway databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public Addon read(String id) {
        JSONObject rawData = databaseConnector.read(tableName, id);
        return loadAddonFromJSON(rawData);
    }

    @Override
    public boolean update(String id, Addon item) {
        return databaseConnector.update(tableName,id, loadJSONfromAddon(item));
    }

    @Override
    public String create(Addon item) {
        return databaseConnector.create(tableName, loadJSONfromAddon(item));
    }

    @Override
    public List<Addon> readMultiple(String parameter, String needle) {
        List<Addon> addonList = new ArrayList<>();
        List<JSONObject> rawAddons = databaseConnector.readMultiple(tableName, parameter, needle);
        for(JSONObject rawAddon: rawAddons){
            addonList.add(loadAddonFromJSON(rawAddon));
        }
        return addonList;
    }

    public JSONObject getAddonTypes(){
        return databaseConnector.getCollection("AddonTypes");


    }

    @Override
    public Addon findOneByFieldName(String fieldName, String needle) {
        return loadAddonFromJSON(databaseConnector.readOne(tableName, fieldName, needle));
    }

    public static JSONObject loadJSONfromAddon(Addon addon){
        return new JSONObject(addon.toString());
    }

    public Addon loadAddonFromJSON(JSONObject addonObj){
        if(addonObj == null){
            return null;
        }
        if(!addonObj.has("id") || !addonObj.has("name") || !addonObj.has("price") ||
                !addonObj.has("addonTypes") || !addonObj.has("isAvailable") || !addonObj.has("shopId")  ){

            throw new InvalidParameterException("Json object does not have the right types.");
        }
        try {
            String id = addonObj.getString("id");
            String name = addonObj.getString("name");
            float price = addonObj.getFloat("price");
            JSONArray addonTypesRaw = addonObj.getJSONArray("addonTypes");
            List<Integer> addonTypes = new ArrayList<>();
            for(int i =0;i<addonTypesRaw.length(); i++){
                addonTypes.add(addonTypesRaw.getInt(i));
            }
            boolean isAvailable = addonObj.getBoolean("isAvailable");
            String shopId = addonObj.getString("shopId");
            return new Addon(id, name,price,addonTypes,isAvailable,shopId);
        }catch(JSONException e){
            return null;
        }
    }
}
