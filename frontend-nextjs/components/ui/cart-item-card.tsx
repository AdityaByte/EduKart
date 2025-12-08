import { Button } from "./button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from "./card";

export default function CartItemCard({
  name,
  category,
  price,
}: {
  name: string;
  category: string;
  price: number;
}) {
  return (
    <Card
      className="
        w-full rounded-(--radius)
        border bg-background
        shadow-sm hover:shadow-md
        transition-all
        px-2
        backdrop-blur
      "
    >
      <CardHeader className="pb-2">
        <div className="flex justify-between items-start">
          <CardTitle className="text-xl font-semibold">{name}</CardTitle>

          <span
            className="
              px-3 py-1
              rounded-[calc(var(--radius)-0.4rem)]
              bg-muted text-xs font-medium
              uppercase tracking-wide
            "
          >
            {category}
          </span>
        </div>
      </CardHeader>

      <CardContent>
        <div className="flex items-center justify-between mt-2">
          <span className="text-base font-medium text-muted-foreground">
            Price
          </span>

          <span className="text-2xl font-semibold">${price}</span>
        </div>
      </CardContent>

      <CardFooter className="pt-4 flex justify-end">
        <Button
          variant="destructive"
          size="lg"
          className="w-full sm:w-auto"
        >
          Remove from Cart
        </Button>
      </CardFooter>
    </Card>
  );
}
