package com.mes.motorph.controller;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.skins.ColorTileSkin;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class DashboardController {
    @FXML
    private Label breadCrumb;
    @FXML
    private GridPane gridPane;

    public void initialize() {
        breadCrumb.setText("Overview / Dashboard");
        // Create ColorTile
        Tile tile1 = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .prefSize(200, 200)
                .title("Employees")
                .textSize(Tile.TextSize.BIGGER) // Increase text size for title
                .textColor(Color.valueOf("#000000"))
                .text("Description")
                .textSize(Tile.TextSize.BIGGER) // Increase text size for description
                .textColor(Color.valueOf("#000000"))
                .leftText("1000") // Place your value here
                .build();



        Tile tile2 = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .prefSize(200, 200)
                .title("Tile 2")
                .text("Description")
                .build();

        Tile tile3 = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .prefSize(200, 200)
                .title("Tile 3")
                .text("Description")
                .build();

        // Set the color of the ColorTile
        tile1.setBackgroundColor(Color.LIGHTBLUE);
        tile2.setBackgroundColor(Color.MINTCREAM);
        tile3.setBackgroundColor(Color.PINK);

        // Add the ColorTile to the GridPane
        gridPane.add(tile1, 0, 0);
        gridPane.add(tile2, 1, 0);
        gridPane.add(tile3, 2, 0);
    }
}
