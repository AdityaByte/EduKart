import { Button } from '@/components/ui/button'
import { ShoppingCart } from 'lucide-react'

const CartButton = () => {
    return (
        <Button
            size='icon'
            className='bg-green-600/10 text-green-600 hover:bg-green-600/20 focus-visible:ring-green-600/20 dark:bg-green-400/10 dark:text-green-400 dark:hover:bg-green-400/20 dark:focus-visible:ring-green-400/40'
        >
            <ShoppingCart />
            <span className='sr-only'>Cart</span>
        </Button>
    )
}

export default CartButton
