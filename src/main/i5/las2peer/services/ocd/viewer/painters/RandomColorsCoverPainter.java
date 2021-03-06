package i5.las2peer.services.ocd.viewer.painters;

import i5.las2peer.services.ocd.graphs.Cover;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomColorsCoverPainter implements CoverPainter {

	@Override
	public void doPaint(Cover cover) {
		List<Color> colors = getColorCollection(cover.communityCount());
		for(int i=0; i<cover.communityCount(); i++) {
			cover.setCommunityColor(i, colors.get(i));
		}
	}
	
	/**
	 * Returns a collection of random colors.
	 * Each color is mixed with a light gray to ensure readability of node labels.
	 * @param amount The amount of colors required.
	 * @return The color collection.
	 */
	protected List<Color> getColorCollection(int amount) {
		List<Color> colors = new ArrayList<Color>();
		for(int i=0; i<amount; i++) {
		    Random random = new Random();
		    int red = random.nextInt(256);
		    int green = random.nextInt(256);
		    int blue = random.nextInt(256);
		    /*
		     * Mix random color with light gray to lighten up.
		     */
		    if (colors != null) {
		        red = (red + 192) / 2;
		        green = (green + 192) / 2;
		        blue = (blue + 192) / 2;
		    }
		    colors.add(new Color(red, green, blue));
		}
	    return colors;
	}

	
}
