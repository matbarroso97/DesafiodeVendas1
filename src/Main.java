import entities.Sale;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        List<Sale> salesList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while (line  != null) {

                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                salesList.add(new Sale(month, year, seller, items, total));

                line = br.readLine();

            }


            System.out.println();
            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");

            List<Sale> result1 = salesList.stream()
                    .filter(s -> s.getYear().equals(2016))
                    .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            result1.forEach(System.out::println);


            double totalLogan = salesList.stream()
                    .filter(s -> s.getSeller().equalsIgnoreCase("Logan"))
                    .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                    .mapToDouble(Sale::getTotal)
                    .sum();



            System.out.printf("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", totalLogan);

        } catch (IOException e) {

            System.out.println("Erro " + e.getMessage());
        }

        sc.close();
    }
}