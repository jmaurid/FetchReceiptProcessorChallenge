package com.fetch.exercise.fetchexercise.util;

import com.fetch.exercise.fetchexercise.item.Item;
import com.fetch.exercise.fetchexercise.receipt.Receipt;

import java.time.LocalTime;
import java.util.List;

public class Points {
    private Integer points;


    /**
     *
     * Returns a Points object with the total of points based on a set of rules.
     *
     * @param receipt Existing Receipt object in database
     * @return Points object with the total of points
     *
     */
    public static Points calculatePoints(Receipt receipt){
        Points points = new Points();
        int total = 0;
        total += calculatePointsForRetailerName(receipt.getRetailer());
        total += calculatePointsForTotalIfRoundDollar(receipt.getTotal());
        total += calculatePointsForTotalIfMultiple(receipt.getTotal());
        total += calculatePointsForEveryTwoItems(receipt.getItems());
        total += calculatePointsForItemDescription(receipt.getItems());
        total += calculatePointsForDayOdd(receipt.getPurchaseDate().getDayOfMonth());
        total += calculatePointsForTime(receipt.getPurchaseTime());
        points.setPoints(total);
        return points;
    }


    /**
     *
     * Returns an Integer that represents sum of points based on retailer name length
     *
     * @param retailer Retailer name of Receipt object in database
     * @return Integer that represents sum of points
     *
     */
    private static Integer calculatePointsForRetailerName(String retailer) {
        return retailer.replaceAll("[^a-zA-Z0-9]", "").length();
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * 50 points if the total is a round dollar amount with no cents
     * 0 points if the total is NOT a round dollar amount with no cents
     *
     * @param total Receipt total of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForTotalIfRoundDollar(Double total) {
        return total % 1 == 0 ? 50 : 0;
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * 25 points if the total is a multiple of 0.25
     * 0 points if the total is NOT a multiple of 0.25
     *
     * @param total Receipt total of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForTotalIfMultiple(Double total) {
        return total % 0.25 == 0 ? 25 : 0;
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * 5 points for every two items on the receipt
     *
     * @param items Retailer items of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForEveryTwoItems(List<Item> items) {
        return items.size() % 2 != 0 ? (((items.size() -1)/2) * 5) : ((items.size()/2) * 5);
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer.
     *
     * @param items Retailer items of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForItemDescription(List<Item> items) {
        int sum = 0;
        for (Item item: items) {
            if(item.getShortDescription().trim().length() % 3 == 0){
                sum += (int) Math.ceil(item.getPrice() * 0.2);
            }
        }
        return sum;
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * 6 points if the day in the purchase date is odd
     *
     * @param dayOfMonth Retailer day of purchase of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForDayOdd(int dayOfMonth) {
        return dayOfMonth % 2 != 0 ? 6 : 0;
    }

    /**
     *
     * Returns the quantity of points based on the following rule:
     * 10 points if the time of purchase is after 2:00pm and before 4:00pm
     *
     * @param purchaseTime Retailer time of purchase of Receipt object in database
     * @return int that represents quantity of points
     *
     */
    private static int calculatePointsForTime(String purchaseTime) {
        LocalTime time = LocalTime.parse(purchaseTime);
        return time.isAfter(LocalTime.of(14,0)) &&
                time.isBefore(LocalTime.of(16,0)) ? 10 : 0;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CalculatePoints{");
        sb.append("points= ").append(points);
        sb.append('}');
        return sb.toString();
    }
}
