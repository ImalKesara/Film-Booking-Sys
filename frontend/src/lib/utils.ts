export const fullname = (name : string) : string =>{
   return name.split(' ')[0].charAt(0).toUpperCase() + name.split(' ')[1].charAt(0).toUpperCase()
}