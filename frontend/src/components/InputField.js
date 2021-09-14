export default function InputField({
  title,
  type,
  name,
  value,
  onChange,
  placeholder,
}) {
  return (
    <div>
      <p>{title}</p>
      <input
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
      />
    </div>
  )
}
