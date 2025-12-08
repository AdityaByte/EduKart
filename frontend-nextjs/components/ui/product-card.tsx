import { useAuth } from "@/context/AuthContext";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./card";
import LikeButton from "./like-button";
import { toast } from "sonner";

export default function ProductCard({
  id,
  name,
  description,
  price,
  category
}: {
  id: string;
  name: string;
  description: string;
  price: number;
  category: string;
}) {

  // Since here we are getting all the things if we want to add a product to the cart we can add that ok.

  const backendURL = process.env.NEXT_PUBLIC_BACKEND_URL;
  const { token } = useAuth();

  const handleAddToCart = async () => {
    // Here we have to make the request to the backend and add to cart.
    if (!token) {
      toast.info("Please login for adding item to cart.");
      return;
    }
    // Else we need to make the request to the backend.
    try {
      const response = await fetch(`${backendURL}/api/cart?id=${id}`, {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      })

      if (!response.ok) {
        throw new Error("Failed to add item to the cart, " + response.status)
      }

      // Else we need to show the message.
      toast.success("Item added to the cart successfully");
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <Card
      className="
          max-w-[360px] w-[340px] mx-auto rounded-2xl
          bg-white border border-gray-200 shadow-sm
          hover:shadow-lg hover:border-gray-300
          transition-all duration-300 cursor-pointer p-0
        "
    >
      <CardHeader className="p-5 pb-3">
        <span
          className="
              inline-block text-[10px] font-semibold text-[#2E2E2E]
              bg-[#F1F1F1] px-2.5 py-1 rounded-full uppercase
              tracking-wider shadow-sm
            "
        >
          {category}
        </span>

        <CardTitle className="text-xl font-semibold mt-3 leading-tight">
          {name}
        </CardTitle>

        <CardDescription className="text-gray-600 leading-relaxed mt-2">
          {description}
        </CardDescription>
      </CardHeader>
      <CardFooter className="p-5 pt-3 gap-1">
        <LikeButton />
        <CardAction
          onClick={handleAddToCart}
          className="
              w-full py-2.5 text-sm font-medium rounded-xl
              bg-[#171717] text-white text-center
              hover:bg-black active:scale-[0.98]
              transition-all duration-300 shadow-sm
            "
        >
          Add to Cart
        </CardAction>
      </CardFooter>
    </Card>
  );
}
