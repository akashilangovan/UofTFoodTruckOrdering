package businessrules.addon.usecases;

import businessrules.addon.inputboundaries.CreateAddonInputBoundary;
import businessrules.dai.AddonRepository;
import businessrules.outputboundary.AddonModel;
import businessrules.outputboundary.ErrorModel;
import entities.Addon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateAddonUseCase implements CreateAddonInputBoundary {

    AddonRepository addonRepository;
    ErrorModel errorHandler;
    AddonModel addonView;

    @Override
    public boolean createAddon(JSONObject data) {
        Addon addon;
        try {
            addon = AddonLoader.loadAddon(data);
        }catch (JSONException e){
            errorHandler.displayError(e.getMessage());
            return false;
        }

        String id = addonRepository.createAddon(addon.jsonify());

        if(id != null) {
            addon.setId(id);
            addonView.displayAddon(addon.jsonify());
            return true;
        }else{
            errorHandler.displayError("Unable to create addon in the repository.");
        }
        return false;

    }



}
