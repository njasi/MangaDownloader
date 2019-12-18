import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        mountApi();
    }

    public static void mountApi() {
        path("/api", () -> {
            mountSearch();
            mountCurrent();
            mountDownload();
        });
    }

    public static void mountSearch() {
        path("/search", () -> {
            get("", (r, re) -> {
                return "oog";
            });
        });
    }

    public static void mountCurrent() {
        path("/current", () -> {
            get("", (r, re) -> {
                return null;
            });
        });
    }

    public static void mountDownload() {
        path("/download", () -> {
            post("", (r, re) -> {
                return null;
            });
        });
    }

}
