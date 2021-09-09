export default function InputField({ title, type, name, value, onChange }) {
  return (
    <div>
      <p>{title}</p>
      <input type={type} name={name} value={value} onChange={onChange} />
    </div>
  )
}
