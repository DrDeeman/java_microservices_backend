package app




class Main;

object Main{
    def main(args: Array[String]):Unit = {

        System.out.printf("Hello and welcome!");
        var arr:List[Integer] = List(1,2,3);

        for (num <- arr) {

            System.out.println("i = " + num);
        }
    }
}