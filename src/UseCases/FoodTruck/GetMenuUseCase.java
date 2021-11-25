package UseCases.FoodTruck;

import Entities.Interfaces.ICustomer;
import Entities.Interfaces.IShop;
import Entities.Interfaces.IUser;
import Entities.Interfaces.IVendor;
import Entities.Menu;
import UseCases.DataAccessInterfaces.FoodTruckRepository;
import UseCases.DataAccessInterfaces.UserRepository;
import UseCases.DataAccessInterfaces.VendorRepository;
import UseCases.OutputBoundary.ErrorPopup;
import UseCases.OutputBoundary.FoodTruckModel;

public class GetMenuUseCase implements GetMenuInputBoundary{
    FoodTruckRepository foodTruckRepository;
    UserRepository userRepository;
    ErrorPopup errorDisplayer;
    public GetMenuUseCase(FoodTruckRepository foodTruckRepository,
                             ErrorPopup errorDisplayer, UserRepository userRepository){
        this.userRepository = userRepository;
        this.errorDisplayer = errorDisplayer;
        this.foodTruckRepository = foodTruckRepository;
    }
    @Override
    public Menu getMenu(String userToken, String shopid) {
        IUser user = userRepository.getUserFromToken(userToken);
        if(user != null){
            IShop foodTruck = foodTruckRepository.getFoodTruck(shopid);
            if(foodTruck != null){
                return foodTruck.getMenu();
            }
            else{
                errorDisplayer.displayError("Invalid ShopID");
            }
        }
        errorDisplayer.displayError("Invalid UserID");
        return null;
    }
}
