import java.util.*;

public class CoffeeMachine {

	public static class	CoffeeMachineWork {
		private int amountOfWater;
		private int amountOfMilk;
		private int amountOfBeans;
		private int amountOfCups;
		private int amountOfMoney;

		CoffeeMachineWork() {
			amountOfWater = 400;
			amountOfMilk = 540;
			amountOfBeans = 120;
			amountOfCups = 9;
			amountOfMoney = 550;
		}

		private void	printInfo() {
			System.out.println();
			System.out.println(amountOfWater + " of water");
			System.out.println(amountOfMilk + " of milk");
			System.out.println(amountOfBeans + " of coffee beans");
			System.out.println(amountOfCups + " of disposable cups");
			System.out.println(amountOfMoney + " of money");
			System.out.println();
		}

		private boolean	checkAmountWater(int neededAmountOfWater) {
			if (this.amountOfWater >= neededAmountOfWater) {
				return (true);
			}
			System.out.println("Sorry, not enough water!");
			return (false);
		}

		private boolean	checkAmountMilk(int neededAmountOfMilk) {
			if (this.amountOfMilk >= neededAmountOfMilk) {
				return (true);
			}
			System.out.println("Sorry, not enough milk!");
			return (false);
		}

		private boolean	checkAmountBeans(int neededAmountOfBeans) {
			if (this.amountOfWater >= neededAmountOfBeans) {
				return (true);
			}
			System.out.println("Sorry, not enough beans!");
			return (false);
		}

		private boolean	checkAmountCups(int neededAmountOfCups) {
			if (this.amountOfWater >= neededAmountOfCups) {
				return (true);
			}
			System.out.println("Sorry, not enough cups!");
			return (false);
		}

		private void	buyEspresso() {
			if (checkAmountWater(250) &&
				checkAmountBeans(16) &&
				checkAmountCups(1)) {
				System.out.println("I have enough resources, making you a coffee!");
				amountOfWater -= 250;
				amountOfBeans -= 16;
				amountOfMoney += 4;
				amountOfCups--;
			}
		}

		private void	buyLatte() {
			if (checkAmountWater(350) &&
				checkAmountMilk(75) &&
				checkAmountBeans(20) &&
				checkAmountCups(1)) {
				System.out.println("I have enough resources, making you a coffee!");
				amountOfWater -= 350;
				amountOfMilk -= 75;
				amountOfBeans -= 20;
				amountOfMoney += 7;
				amountOfCups--;
			}
		}

		private void	buyCappuccino() {
			if (checkAmountWater(200) &&
				checkAmountMilk(100) &&
				checkAmountBeans(12) &&
				checkAmountCups(1)) {
				System.out.println("I have enough resources, making you a coffee!");
				amountOfWater -= 200;
				amountOfMilk -= 100;
				amountOfBeans -= 12;
				amountOfMoney += 6;
				amountOfCups--;
			}
		}

		private void	buyCoffee(Scanner scan) {
			String coffee;

			System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
			coffee = scan.nextLine();
			switch (coffee) {
				case "1":
					buyEspresso();
					break;
				case "2":
					buyLatte();
					break;
				case "3":
					buyCappuccino();
					break;
				case "back":
					return;
				default:
					System.out.println("Wrong command");
					break;
			}
		}

		private void	fillCoffeeMachine(Scanner scan) {
			System.out.println("Write how many ml of water do you want to add: ");
			amountOfWater += scan.nextInt();

			System.out.println("Write how many ml of milk do you want to add: ");
			amountOfMilk += scan.nextInt();

			System.out.println("Write how many grams of coffee beans do you want to add: ");
			amountOfBeans += scan.nextInt();

			System.out.println("Write how many disposable cups of coffee do you want to add: ");
			amountOfCups += scan.nextInt();
		}

		private void	takeMoney() {
			System.out.println("I gave you $" + amountOfMoney);
			amountOfMoney = 0;
		}
	}

	public static void	main(String[] args) {
		String				command;
		Scanner				scan = new Scanner(System.in);
		CoffeeMachineWork	machine = new CoffeeMachineWork();

		for(;;) {
			System.out.println("Write action (buy, fill, take, remaining, exit): ");
			command = scan.nextLine();
			if ("exit".equals(command)) {
				break;
			}
			switch (command) {
				case "buy":
					machine.buyCoffee(scan);
					break;
				case "fill":
					machine.fillCoffeeMachine(scan);
					break;
				case "take":
					machine.takeMoney();
					break;
				case "remaining":
					machine.printInfo();
					break;
				default:
					System.out.println("Wrong command");
					break;
			}
		}
	}
}
