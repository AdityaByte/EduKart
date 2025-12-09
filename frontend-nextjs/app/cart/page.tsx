"use client";

import { Button } from "@/components/ui/button";
import CartItemCard from "@/components/ui/cart-item-card";
import { useAuth } from "@/context/AuthContext";
import React from "react";

export default function Cart() {

    type CartItemType = {
        id: string;
        name: string;
        category: string;
        price: number;
    }

    type CartType = {
        id: string;
        cartItemList: CartItemType[];
    }

    const backendURL = process.env.NEXT_PUBLIC_BACKEND_URL;
    const { token } = useAuth();
    const [total, setTotal] = React.useState<number>(0);
    const [cart, setCart] = React.useState<CartType>({
        id: "",
        cartItemList: [],
    });

    React.useEffect(() => {
        const fetchCart = async () => {
            try {
                const response = await fetch(`${backendURL}/api/cart`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    throw new Error(`Failed to fetch cart: ${response.status}`);
                }

                const data = await response.json();
                console.log(data)
                setCart(data);
                setTotal(
                    data.cartItemList.reduce(
                        (total: number, current: CartItemType) => total + current.price,
                        0
                    )
                );
            } catch (error) {
                console.error("Error fetching cart:", error);
            }
        };
        fetchCart();
    }, [backendURL, token]);

    const handlePlaceOrder = async () => {
        try {
            const response = await fetch(`${backendURL}/api/order`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })

            if (!response.ok) {
                throw new Error("Failed to place order, " + response.body);
            }

            const data = await response.json();

            if (data.status !== "PENDING") {
                throw new Error("Failed to generate the payment URL, " + data.status);
            }

            // Else we need to redirect the window to the payment URL.
            window.location.href = data.paymentURL;
        } catch (error) {
            console.error(error)
        }
    }


    return (
        <main className="overflow-hidden pt-10 pb-10">
            <section className="relative">
                <div
                    className="
            absolute inset-0 -top-10 left-1/2 -z-20 h-80 w-full -translate-x-1/2
            [background-image:linear-gradient(to_bottom,transparent_98%,theme(colors.gray.200/70%)_98%),linear-gradient(to_right,transparent_94%,theme(colors.gray.200/70%)_94%)]
            [background-size:16px_35px]
            [mask:radial-gradient(black,transparent_80%)]
            dark:opacity-10
          "
                />
                <div
                    className="
            absolute inset-x-0 top-40 -z-[1] mx-auto
            h-1/3 w-2/3 rounded-full bg-blue-300 blur-3xl
            dark:bg-white/20
          "
                />
                <div className="mx-auto max-w-7xl px-6 md:px-12">

                    <div className=" grid grid-cols-1 lg:grid-cols-3 gap-10">
                        <div className="col-span-2 bg-background rounded-(--radius) border p-6 shadow-sm backdrop-blur">
                            <h2 className="text-xl font-semibold mb-6">Items in Cart</h2>

                            <div className="flex flex-col gap-5">
                                {cart.cartItemList.map((item, index) => (
                                    <CartItemCard
                                        key={item.id}
                                        name={item.name}
                                        category={item.category}
                                        price={item.price}
                                    />
                                ))}
                            </div>

                            <div className="mt-10 border-t pt-6 flex justify-end">
                                <Button onClick={handlePlaceOrder} size="lg" className="bg-green-700 text-white cursor-pointer">
                                    Place Order
                                </Button>
                            </div>
                        </div>

                        <div className="bg-background rounded-(--radius) border p-6 shadow-sm backdrop-blur">
                            <h2 className="text-xl font-semibold">Order Summary</h2>
                            <p className="mt-1 text-muted-foreground text-sm">
                                Final breakdown of your selected items
                            </p>

                            <div className="mt-6 space-y-4 text-sm">
                                <div className="flex justify-between">
                                    <span>Subtotal</span>
                                    <span className="font-medium">₹ {total}</span>
                                </div>

                                <div className="flex justify-between">
                                    <span>Discount</span>
                                    <span className="font-medium text-green-700">₹ 0</span>
                                </div>

                                <div className="flex justify-between border-t pt-3">
                                    <span className="font-semibold text-lg">Total</span>
                                    <span className="font-bold text-lg">₹ {total}</span>
                                </div>
                            </div>

                            <Button size="lg" className="w-full mt-6 cursor-pointer">
                                Checkout Securely
                            </Button>
                        </div>

                    </div>
                </div>
            </section>
        </main>
    );
}
