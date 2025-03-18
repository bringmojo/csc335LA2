
/*
 * Author: Jifei Wang, Lanyue Zhang(LA2 part change)
 */
import model.LibraryModel;
import model.MusicStore;
import view.Viewer;

public class Main {

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        MusicStore musicStore = new MusicStore();
        LibraryModel libraryModel = new LibraryModel();
        viewer.run(musicStore, libraryModel);
    }
}
