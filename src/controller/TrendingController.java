/*-----------------------------------------------------------------------------------------
 * NAME : TrendingController.java
 * VER  : v0.1
 * PROJ : Akara
 * CODE CLEAN? : No
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION
 * ----------  --------------  ------------------------------------------------------------
 * 2022-07-30   Nuth Vireak     creation
 * ----------  --------------  ------------------------------------------------------------
 * 2022-08-22   Nuth Vireak     Modification
 *---------------------------------------------------------------------------------------*/

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Podcast;
import podcastData.DataInitializer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrendingController implements Initializable {

    //------------------------------------------------------------------------------------
    // fields declaration                                                               |
    //------------------------------------------------------------------------------------

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox recentTrendingContainer;

    @FXML
    private Label seeAllTrendingPodcast;

    @FXML
    private HBox trendingThisWeekContainer;

    List<Podcast> recentTrendingPodcast;
    List<Podcast> trendingThisWeekPodcast;

    //------------------------------------------------------------------------------------
    //  Methods declaration                                                              |
    //------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recentTrendingPodcast = new ArrayList<>(getPopularPodcast());
        trendingThisWeekPodcast = new ArrayList<>(getPopularPodcast());

        try {
            for (Podcast podcast : recentTrendingPodcast) {
                AnchorPane anchorPane = getAnchorPane(podcast);
                recentTrendingContainer.getChildren().add(anchorPane);
            }

            for (Podcast podcast : trendingThisWeekPodcast) {
                AnchorPane anchorPane = getAnchorPane(podcast);
                trendingThisWeekContainer.getChildren().add(anchorPane);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Podcast> getPopularPodcast() {

        DataInitializer dataInitializer = new DataInitializer();
        List<Podcast> popularPodcast = new ArrayList<>();

        for (Podcast podcast : dataInitializer.podcastList()) {
            if (podcast.getViewCount() > 5000) {
                if (popularPodcast.size() < 10) {
                    popularPodcast.add(podcast);
                }}}
        return popularPodcast;
    }

    @FXML
    void seeAllClick(MouseEvent event) throws IOException {

        BorderPane trendingSeeAll = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TrendingSeeAll.fxml")));
        borderPane.setCenter(trendingSeeAll);

        TrendingSeeAllController.setPopularPodcastToView();
    }

    private AnchorPane getAnchorPane(Podcast podcast) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/podcastVbox.fxml"));

        AnchorPane anchorPane = fxmlLoader.load();
        PodcastVboxController podcastVboxController = fxmlLoader.getController();
        podcastVboxController.setData(podcast);
        return anchorPane;
    }

}
