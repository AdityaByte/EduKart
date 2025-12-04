"use client";

import { UserIcon, SettingsIcon, BellIcon, LogOutIcon, CreditCardIcon } from 'lucide-react'

import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu'
import { Avatar, AvatarFallback, AvatarImage } from '@radix-ui/react-avatar'
import { useAuth } from '@/context/AuthContext'
import { toast } from 'sonner'

const listItems = [
  {
    icon: UserIcon,
    property: 'Profile'
  },
  {
    icon: SettingsIcon,
    property: 'Settings'
  },
  {
    icon: BellIcon,
    property: 'Orders'
  },
  {
    icon: LogOutIcon,
    property: 'Sign Out'
  }
]

const ProfileMenu = () => {

  const { user, logout } = useAuth();
  const handleClick = async (action: string) => {
    switch (action) {
      case "Sign Out":
        await logout();
        toast.success("Logout successful");
        break;

      default:
        toast.error("We don't know about this event right now.")
        break;
    }
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='secondary' size='icon' className='overflow-hidden rounded-full'>
          <Avatar className="size-10 flex items-center justify-center bg-white">
            <AvatarImage
              src="https://tailus.io/images/reviews/shekinah.webp"
              alt={user?.displayName ?? ""}
              height="200"
              width="200"
              loading="lazy"
            />
            <AvatarFallback>
              {(() => {
                if (!user?.displayName) return "AP"; // fallback initials
                const names = user.displayName.split(" ");
                const firstInitial = names[0]?.[0] ?? "";
                const secondInitial = names[1]?.[0] ?? "";
                return `${firstInitial}${secondInitial}` || "AP";
              })()}
            </AvatarFallback>
          </Avatar>
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className='w-56'>
        <DropdownMenuLabel>My Account</DropdownMenuLabel>
        <DropdownMenuGroup>
          {listItems.map((item, index) => (
            <DropdownMenuItem key={index} onClick={() => handleClick(item.property)}>
              <item.icon />
              <span className='text-popover-foreground'>{item.property}</span>
            </DropdownMenuItem>
          ))}
        </DropdownMenuGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}

export default ProfileMenu;
