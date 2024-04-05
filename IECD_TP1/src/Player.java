import java.util.Scanner;

public class Player {

    private String name;
    private int age;
    private String password;
    private String nationality;
    private int winCount;

    // Para Login de um jogador
    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.winCount = 0;
    }

    // Para registo de um novo jogador
    public Player(String name, String password, String nationality, int age) {
        this.name = name;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.winCount = 0;
    }

    public static Player newPlayer() {
        Scanner scanner = new Scanner(System.in);
        Player player;

        System.out.print("Nome: ");
        String player_name = scanner.next();
        System.out.print("Password: ");
        String player_password = scanner.next();
        System.out.print("Nacionalidade: ");
        String player_nationality = scanner.next();
        System.out.print("Idade: ");
        int player_age = scanner.nextInt();
        player = new Player(player_name, player_password, player_nationality, player_age);

        return player;
    }

    public static Player playerForLogin() {
        Scanner scanner = new Scanner(System.in);
        Player player;

        System.out.print("Nome: ");
        String player_name = scanner.next();
        System.out.print("Password: ");
        String player_password = scanner.next();
        player = new Player(player_name, player_password);

        return player;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNationality() {
        return this.nationality;
    }

    public int getAge() {
        return this.age;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

}
