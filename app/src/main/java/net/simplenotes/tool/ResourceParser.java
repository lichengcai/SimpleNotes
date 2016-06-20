package net.simplenotes.tool;

import android.content.Context;

import net.simplenotes.R;

/**
 * Created by lichengcai on 2016/6/20.
 */
public class ResourceParser {
    public static final int YELLOW = 0;
    public static final int BLUE = 1;
    public static final int WHITE = 2;
    public static final int GREEN = 3;
    public static final int RED = 4;

    public static final int BG_DEFAULT_COLOR = YELLOW;

    public static final int TEXT_SMALL = 0;
    public static final int TEXT_MEDIUM = 1;
    public static final int TEXT_LARGE = 2;
    public static final int TEXT_SUPER = 3;

    public static final int BG_DEFAULT_FONT_SIZE = TEXT_MEDIUM;

    public static class NoteBgResources {
        private final static int[] BG_EDIT_RESOURCES = new int[]{
                R.drawable.edit_blue,
                R.drawable.edit_green,
                R.drawable.edit_red,
                R.drawable.edit_yellow,
                R.drawable.edit_white
        };

        private final static int[] BG_EDIT_TITLE_RESOURCES = new int[]{
                R.drawable.edit_title_blue,
                R.drawable.edit_title_green,
                R.drawable.edit_title_yellow,
                R.drawable.edit_title_red,
                R.drawable.edit_title_white
        };

        public static int getNoteBgResource(int id) {
            return BG_EDIT_RESOURCES[id];
        }

        public static int getNoteTitleBgResource(int id) {
            return BG_EDIT_TITLE_RESOURCES[id];
        }
    }

    public static int getDefaultBgId(Context context) {
        return BG_DEFAULT_COLOR;
    }

    public static class NoteItemBgResources {
        private final static int[] BG_FIRST_RESOURCES = new int[] {
                R.drawable.list_yellow_up,
                R.drawable.list_blue_up,
                R.drawable.list_green_up,
                R.drawable.list_white_up,
                R.drawable.list_red_up
        };

        private final static int[] BG_LAST_RESOURCES = new int[] {
                R.drawable.list_red_down,
                R.drawable.list_green_down,
                R.drawable.list_blue_down,
                R.drawable.list_yellow_down,
                R.drawable.list_white_down
        };

        private final static int[] BG_NORMAL_RESOURCES = new int[] {
                R.drawable.list_white_middle,
                R.drawable.list_red_middle,
                R.drawable.list_green_middle,
                R.drawable.list_blue_middle,
                R.drawable.list_yellow_middle
        };

        private final static int[] BG_SINGLE_RESOURCES = new int[] {
                R.drawable.list_yellow_single,
                R.drawable.list_blue_single,
                R.drawable.list_green_single,
                R.drawable.list_red_single,
                R.drawable.list_white_single
        };

        public static int getNoteBgFirstRes(int id) {
            return BG_FIRST_RESOURCES[id];
        }

        public static int getNoteBgLastRes(int id) {
            return BG_LAST_RESOURCES[id];
        }

        public static int getNoteBgSingleRes(int id) {
            return BG_SINGLE_RESOURCES[id];
        }

        public static int getNoteBgNormalRes(int id) {
            return BG_NORMAL_RESOURCES[id];
        }

        public static int getFolderBgRes() {
            return R.drawable.list_folder;
        }
    }

    public static class WidgetBgResources {
        private final static int[] BG_2X_RESOURCES = new int[] {
                R.drawable.widget_2x_blue,
                R.drawable.widget_2x_green,
                R.drawable.widget_2x_red,
                R.drawable.widget_2x_white,
                R.drawable.widget_2x_yellow
        };

        public static int getWidget2xBgResource(int id) {
            return BG_2X_RESOURCES[id];
        }

        private final static int[] BG_4X_RESOURCES = new int[] {
                R.drawable.widget_4x_blue,
                R.drawable.widget_4x_green,
                R.drawable.widget_4x_red,
                R.drawable.widget_4x_yellow,
                R.drawable.widget_4x_white
        };

        public static int getWidget4xBgResource(int id) {
            return BG_4X_RESOURCES[id];
        }
    }

    public static class TextAppearanceResources {
        private final  static int[] TEXTAPPEARANCE_RESOURCES = new int[] {
                R.style.TextAppearanceLarge,
                R.style.TextAppearanceMedium,
                R.style.TextAppearanceNormal,
                R.style.TextAppearanceSuper
        };

        public static int getTextAppearanceResource(int id) {
            if (id >= TEXTAPPEARANCE_RESOURCES.length) {
                return BG_DEFAULT_FONT_SIZE;
            }
            return TEXTAPPEARANCE_RESOURCES[id];
        }

        public static int getResourcesSize() {
            return TEXTAPPEARANCE_RESOURCES.length;
        }
    }
}
