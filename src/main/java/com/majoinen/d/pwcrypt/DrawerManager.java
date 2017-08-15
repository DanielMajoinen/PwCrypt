package com.majoinen.d.pwcrypt;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class DrawerManager {

    private final NavigationDrawer drawer;

    public DrawerManager() {
        this.drawer = new NavigationDrawer();

        NavigationDrawer.Header header = new NavigationDrawer.Header(
          "Gluon Mobile", "Multi View Project", new Avatar(21,
          new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);

        final Item primaryItem = new ViewItem("Primary", MaterialDesignIcon.HOME
          .graphic(), ViewManager.LOGIN_VIEW, ViewStackPolicy.SKIP);
        final Item secondaryItem = new ViewItem("Secondary",
          MaterialDesignIcon.DASHBOARD.graphic(), ViewManager.REGISTER_VIEW);
        drawer.getItems().addAll(primaryItem, secondaryItem);

        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit",
              MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class)
                      .ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }

        drawer.addEventHandler(NavigationDrawer.ITEM_SELECTED, e ->
          PwCrypt.getInstance().hideLayer(ViewManager.MENU_LAYER));

        PwCrypt.getInstance().viewProperty().addListener(
          (obs, oldView, newView) -> updateItem(newView.getName()));
        updateItem(ViewManager.LOGIN_VIEW);
    }

    private void updateItem(String nameView) {
        for (Node item : drawer.getItems()) {
            if (item instanceof ViewItem && ((ViewItem) item).getViewName()
              .equals(nameView)) {
                drawer.setSelectedItem(item);
                break;
            }
        }
    }

    public NavigationDrawer getDrawer() {
        return drawer;
    }
}
