package UseCases.DataAccessInterfaces;

import Entities.Interfaces.IVendor;
import Entities.Regular.RegularVendor;

/**
 * The VendorAccessInterface Interface
 *
 * This is an interface that allows for the attainment, changing, deletion, and validation
 * of vendors in the higher level data storage.
 */
public interface VendorRepository extends UserRepository{

    /**
     *  A method that returns the desired vendor from
     *  the higher level data storage.
     *
     * @param id The associated id of the vendor.
     * @return Return the vendor associated with the id.
     */
    public RegularVendor getVendor(String id);


    boolean save(IVendor vendor);
}
