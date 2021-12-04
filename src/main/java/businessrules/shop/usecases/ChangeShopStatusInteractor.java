package businessrules.shop.usecases;

import businessrules.dai.Repository;
import businessrules.dai.VendorRepository;
import businessrules.outputboundaries.ObjectBoundary;
import businessrules.outputboundaries.RepositoryBoundary;
import businessrules.outputboundaries.ResponseObject;
import businessrules.shop.inputboundaries.ChangeShopStatus;
import EntitiesUnitTest.Shop;
import EntitiesUnitTest.Vendor;

public class ChangeShopStatusInteractor implements ChangeShopStatus {
    VendorRepository vendorRepository;
    Repository<Shop> shopRepository;
    RepositoryBoundary repositoryBoundary;
    ObjectBoundary<Shop> shopObjectBoundary;

    @Override
    public ResponseObject changeShopStatus(String vendorToken, boolean newStatus) {

        Vendor vendor = (Vendor) vendorRepository.getUserFromToken(vendorToken);
        if(vendor == null){
            return repositoryBoundary.queryNotFound("No such vendor found.");
        }

        Shop shop = vendor.getShop();
        shop.setOpen(newStatus);
        if(!shopRepository.update(shop.getId(), shop)){
            return repositoryBoundary.modificationFailed("Failed to update shop status in repository.");
        }

        return shopObjectBoundary.showObject(shop);
    }
}
