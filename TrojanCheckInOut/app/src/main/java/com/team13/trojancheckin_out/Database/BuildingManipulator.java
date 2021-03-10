package com.team13.trojancheckin_out.Database;

import com.team13.trojancheckin_out.UPC.Building;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This class communicates with the Buildings database to determine the configuration of the
 * buildings located on UPC. This class can read from CSV files, check QRCode validity, and
 * return a list of available buildings and their accompanying QRCodes.
 */
public class BuildingManipulator {
    private Map<String, Building> currentBuildings;
    private List<String> currentQRCodes;

    /**
     * @return a list of the currently established buildings.
     */
    public Map<String, Building> getCurrentBuildings() { return this.currentBuildings; }

    /**
     * @return a list of the currently established QRCodes.
     */
    public List<String> getCurrentQRCodes() { return this.currentQRCodes; }

    /**
     * @param file
     * @return true if the CSV file has been successfully processed.
     */
    public Boolean processCSV(File file) { return true; }

    /**
     * @param QRCode
     * @return true if the given QRCode has been successfully matched with an existing one.
     */
    public Boolean QRMatch(String QRCode) { return true; }
}
